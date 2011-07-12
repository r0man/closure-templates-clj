(ns closure.templates.compile
  (:refer-clojure :exclude (compile))
  (:import [clojure.lang IPersistentSet IPersistentVector ISeq]
           [java.net URI URL]
           com.google.template.soy.tofu.SoyTofu
           java.io.File)
  (:use closure.templates.fileset
        closure.templates.soy))

(defprotocol Compile
  (compile [object]
    "Compile object into a Soy Tofu."))

(extend-type nil
  Compile
  (compile [_]
    nil))

(extend-type File
  Compile
  (compile [file]
    (compile-fileset [(soy-file file)])))

(extend-type IPersistentSet
  Compile
  (compile [set]
    (compile (seq set))))

(extend-type IPersistentVector
  Compile
  (compile [vector]
    (compile (seq vector))))

(extend-type ISeq
  Compile
  (compile [seq]
    (compile-fileset (set (map soy-file seq)))))

(extend-type SoyTofu
  Compile
  (compile [tofu]
    tofu))

(extend-type String
  Compile
  (compile [path]
    (compile (File. path))))

(extend-type URI
  Compile
  (compile [uri]
    (compile (File. uri))))

(extend-type URL
  Compile
  (compile [url]
    (compile (.toURI url))))
