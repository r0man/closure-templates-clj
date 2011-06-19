(ns closure.templates.soy
  (:import java.io.File))

(defn soy-file?
  "Returns true if file is a SOY template, otherwise false."
  [file]
  (let [file (File. (str file))]
    (and (.isFile file) (.endsWith (.getPath file) ".soy"))))

(defn soy-file-seq
  "Returns a seq of java.io.File objects for all SOY templates found
  in the given directory."
  [directory] (filter soy-file? (file-seq (File. (str directory)))))

(defn soy-uri-seq
  "Returns a seq of java.net.URI objects for all SOY templates found
  in the given directory."
  [directory] (map #(.toURI %) (soy-file-seq directory)))
