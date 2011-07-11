(ns closure.templates.render
  (:refer-clojure :exclude (compile))
  (:import [java.net URI URL]
           clojure.lang.ISeq java.io.File com.google.template.soy.tofu.SoyTofu)
  (:use [clojure.contrib.string :only (as-str)]
        [clojure.walk :only (postwalk)]
        closure.templates.compile))

(defprotocol Render
  (render [object template data bundle]
    "Render the Soy template."))

(defn underscore-keys
  "Recursively transforms all map keys to strings and replaces all
  dashes in the map keys with underscores."
  [m] (let [f (fn [[k v]]
                [(.replace (as-str k) "-" "_")
                 (if (map? v) (underscore-keys v) v)])]
        (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(extend-type nil
  Render
  (render [_ _ _ _]
    nil))

(extend-type File
  Render
  (render [file template data bundle]
    (render (compile file) template data bundle)))

(extend-type ISeq
  Render
  (render [seq template data bundle]
    (render (compile seq) template data bundle)))

(extend-type Iterable
  Render
  (render [iterable template data bundle]
    (render (seq iterable) template data bundle)))

(extend-type SoyTofu
  Render
  (render [tofu template data bundle]
    (.render tofu template (underscore-keys data) bundle)))

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
