(ns closure.templates.render
  (:refer-clojure :exclude (compile))
  (:import [clojure.lang IPersistentSet IPersistentVector ISeq]
           [java.net URI URL]
           java.io.File com.google.template.soy.tofu.SoyTofu)
  (:use [clojure.contrib.string :only (as-str)]
        [clojure.walk :only (stringify-keys)]
        [inflections.core :only (underscore-keys)]
        closure.templates.compile))

(defprotocol Render
  (render [object template data bundle]
    "Render the Soy template."))

(extend-type nil
  Render
  (render [_ _ _ _]
    nil))

(extend-type File
  Render
  (render [file template data bundle]
    (render (compile file) template data bundle)))

(extend-type IPersistentSet
  Render
  (render [set template data bundle]
    (render (seq set) template data bundle)))

(extend-type IPersistentVector
  Render
  (render [vector template data bundle]
    (render (seq vector) template data bundle)))

(extend-type ISeq
  Render
  (render [seq template data bundle]
    (render (compile seq) template data bundle)))

(extend-type SoyTofu
  Render
  (render [tofu template data bundle]
    (.render tofu template (underscore-keys (stringify-keys data)) bundle)))

(extend-type String
  Render
  (render [path template data bundle]
    (render (compile path) template data bundle)))

(extend-type URI
  Render
  (render [uri template data bundle]
    (render (File. uri) template data bundle)))

(extend-type URL
  Render
  (render [url template data bundle]
    (render (.toURI url) template data bundle)))
