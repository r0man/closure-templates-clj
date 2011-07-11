(ns closure.templates.soy
  (:refer-clojure :exclude (replace))
  (:import java.io.File)
  (:use [clojure.contrib.def :only (defvar)]
        [clojure.string :only (blank? replace)]
        [inflections.core :only (camelize underscore)]))

(defvar *directory* "soy"
  "The directory on the classpath containing the Soy template files.")

(defvar *extension* "soy"
  "The filename extension of Soy template files.")

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
