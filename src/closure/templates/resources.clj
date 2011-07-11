(ns closure.templates.resources
  (:refer-clojure :exclude (replace))
  (:import [com.google.template.soy SoyFileSet$Builder] java.io.File)
  (:use [clojure.contrib.def :only (defvar)]
        [clojure.contrib.string :only (as-str)]
        [clojure.string :only (blank? replace)]
        [clojure.walk :only (postwalk stringify-keys)]
        [inflections.core :only (camelize underscore)]
        closure.templates.classpath))

(defvar *directory* "soy"
  "The directory on the classpath containing the Soy template files.")

(defvar *extension* "soy"
  "The filename extension of Soy template files.")

(defvar *fileset* (ref #{})
  "A reference to the compiled Soy files.")

(defn add-soy-file!
  "Add the Soy file to *fileset*."
  [file] {:pre [(.isFile file)]}
  (dosync (alter *fileset* conj file)))

(defn clear-fileset!
  "Remove all Soy files from *fileset*."
  [] (dosync (ref-set *fileset* #{})))

(defn compile-fileset
  "Compile the Soy fileset into a Tofu object."
  [fileset]
  (let [builder (SoyFileSet$Builder.)]
    (doall (map #(.add builder %) fileset))
    (.compileToJavaObj (.build builder))))

(defn soy-file?
  "Returns true if file is a regular file and the filename ends with
  '.soy', otherwise false."
  [file]
  (let [file (File. (str file))]
    (and (.isFile file) (.endsWith (.getPath file) (str "." *extension*)))))

(defn soy-file
  "Make a Soy file. Returns a java.io.File instance or throws an
  IllegalArgumentException if the file is not a Soy file."
  [file]
  (if (soy-file? file)
    (File. (str file))
    (throw (IllegalArgumentException. (str "Not a Soy file: " file)))))

(defn soy-file-seq
  "Returns a seq of java.io.File objects which contains all Soy
  template files found in directory."
  [directory] (map soy-file (filter soy-file? (file-seq (File. (str directory))))))

(defn template-name
  "Returns the template name by replacing all '/' characters with a
  '.' and camelizing the names between dots."
  [name] {:pre [(not (blank? name))]}
  (camelize (replace name "/" ".") :lower))

(defn template-path
  "Returns the filename of the template relative to the classpath."
  [name] {:pre [(not (blank? name))]}
  (str *directory* "/"
       (replace (underscore (replace name #"/.*" "")) #"\." File/separator)
       "." *extension*))

(defn underscore-keys
  "Recursively transforms all map keys to strings and replaces all
  dashes in the map keys with underscores."
  [m] (let [f (fn [[k v]]
                [(.replace (as-str k) "-" "_")
                 (if (map? v) (underscore-keys v) v)])]
        (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn render-template
  "Render the template in the Tofu with data."
  [tofu template data & [bundle]]
  (.render tofu template (underscore-keys data) bundle))
