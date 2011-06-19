(ns closure.templates.classpath
  (:import [java.net URI URL]
           [java.io File FileNotFoundException]))

(defn classpath-url
  "Returns a java.net.URL instance of the resource on the classpath or
  throws a FileNotFoundException if the resource doesn't exist."
  [resource]
  (or (.getResource (clojure.lang.RT/baseLoader) resource)
      (throw (FileNotFoundException. (str "Can't find resource on classpath: " resource)))))

(defn classpath-uri
  "Returns a java.net.URI instance of the resource on the classpath or
  throws a FileNotFoundException if the resource doesn't exist."
  [resource] (URI. (str (classpath-url resource))))

(defn classpath-file
  "Returns a java.io.File instance of the resource on the classpath or
  throws a FileNotFoundException if the resource doesn't exist."
  [resource] (File. (classpath-uri resource)))

