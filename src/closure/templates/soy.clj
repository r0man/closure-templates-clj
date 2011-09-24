(ns #^{:doc "Soy helpers." :author "Roman Scherer"}
  closure.templates.soy
  (:refer-clojure :exclude (replace))
  (:import java.io.File java.net.URL java.net.URI)
  (:use [clojure.string :only (blank? replace)]
        [inflections.core :only (camelize underscore)]))

(def ^:dynamic *directory* "soy")
(def ^:dynamic *extension* "soy")

(defprotocol Soy
  (soy [object] "Make a Soy. Returns a java.net.URL instance or throws
  an IllegalArgumentException if object is not a Soy."))

(defn soy?
  "Returns true if the resource ends with '.soy', otherwise false."
  [resource] (.endsWith (str resource) (str "." *extension*)))

(defn soy-seq
  "Returns a seq of java.net.URL objects that contains all Soy
  template files found in directory."
  [directory] (map soy (filter soy? (file-seq (File. (str directory))))))

(defn fn-js
  "Returns the template name by replacing all '/' characters with a
  '.' and camelizing the names between dots."
  [name & [ns]] {:pre [(not (blank? (str name)))]}
  (camelize (replace (str (or ns *ns*) "/" name) "/" ".") :lower))

(defn fn-path
  "Returns the filename of the template relative to the classpath."
  [name & [ns]] {:pre [(not (blank? (str name)))]}
  (str *directory* File/separator
       (replace (underscore (str (or ns *ns*))) #"\." File/separator)
       "." *extension*))

(extend-type Object
  Soy
  (soy [object]
    (throw (IllegalArgumentException. (str "Not a Soy: " object)))))

(extend-type File
  Soy
  (soy [file]
    (if (.exists file)
      (soy (.toURL file))
      (throw (IllegalArgumentException. (str "Soy doesn't exist: " file))))))

(extend-type String
  Soy
  (soy [string] (soy (File. string))))

(extend-type URI
  Soy
  (soy [uri] (.toURL uri)))

(extend-type URL
  Soy
  (soy [url]
    (if (soy? url)
      url (throw (IllegalArgumentException. (str "Not a Soy: " url))))))
