(ns #^{:doc "Compilation of Soy templates." :author "Roman Scherer"}
  closure.templates.compile
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
    (compile (.toURL file))))

(extend-type IPersistentSet
  Compile
  (compile [set]
    (compile-fileset (map soy set))))

(extend-type IPersistentVector
  Compile
  (compile [vector]
    (compile (set vector))))

(extend-type ISeq
  Compile
  (compile [seq]
    (compile (set (map soy seq)))))

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
    (compile (.toURL uri))))

(extend-type URL
  Compile
  (compile [url]
    (compile-fileset [url])))
