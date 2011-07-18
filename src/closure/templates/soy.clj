(ns closure.templates.soy
  (:refer-clojure :exclude (replace))
  (:import java.io.File java.net.URL java.net.URI)
  (:use [clojure.contrib.def :only (defvar)]
        [clojure.string :only (blank? replace)]
        [inflections.core :only (camelize underscore)]))

(defvar *directory* "soy"
  "The directory on the classpath containing the Soy template files.")

(defvar *extension* "soy"
  "The filename extension of Soy template files.")

(defn soy?
  "Returns true if file is a regular file and the filename ends with
  '.soy', otherwise false."
  [file] (.endsWith (str file) (str "." *extension*)))

(defn soy
  "Make a Soy. Returns a java.net.URL instance or throws an
  IllegalArgumentException if the file is not a Soy file."
  [file]
  (if (soy? file)
    (cond
     (isa? (class file) URL) file
     (isa? (class file) URI) (.toURL file)
     (isa? (class file) File) (.toURL file)
     :else (.toURL (File. file)))
    (throw (IllegalArgumentException. (str "Not a Soy file: " file)))))

(defn soy-seq
  "Returns a seq of java.net.URL objects which contains all Soy
  template files found in directory."
  [directory] (map #(.toURL %) (filter soy? (file-seq (File. (str directory))))))

(defn template-name
  "Returns the template name by replacing all '/' characters with a
  '.' and camelizing the names between dots."
  [name & [ns]] {:pre [(not (blank? (str name)))]}
  (camelize (replace (str (or ns *ns*) "/" name) "/" ".") :lower))

(defn template-path
  "Returns the filename of the template relative to the classpath."
  [name & [ns]] {:pre [(not (blank? (str name)))]}
  (str *directory* File/separator
       (replace (underscore (str (or ns *ns*))) #"\." File/separator)
       "." *extension*))
