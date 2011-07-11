(ns closure.templates.protocol
  (:refer-clojure :exclude (compile))
  (:import [java.net URI URL]
           clojure.lang.ISeq java.io.File com.google.template.soy.tofu.SoyTofu)
  (:use [clojure.contrib.string :only (as-str)]
        [clojure.walk :only (postwalk)]
        closure.templates.fileset
        closure.templates.soy))

(defprotocol Compile
  (compile [object]
    "Compile object into a Soy Tofu."))

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
  Compile
  (compile [_]
    nil)
  Render
  (render [_ _ _ _]
    nil))

(extend-type File
  Compile
  (compile [file]
    (compile-fileset [(soy-file file)]))
  Render
  (render [file template data bundle]
    (render (compile file) template data bundle)))

(extend-type ISeq
  Compile
  (compile [seq]
    (compile-fileset (set (map soy-file seq))))
  Render
  (render [seq template data bundle]
    (render (compile seq) template data bundle)))

(extend-type Iterable
  Compile
  (compile [iterable]
    (compile (seq iterable)))
  Render
  (render [iterable template data bundle]
    (render (seq iterable) template data bundle)))

(extend-type SoyTofu
  Compile
  (compile [tofu]
    tofu)
  Render
  (render [tofu template data bundle]
    (.render tofu template (underscore-keys data) bundle)))

(extend-type String
  Compile
  (compile [path]
    (compile (File. path)))
  Render
  (render [path template data bundle]
    (render (compile path) template data bundle)))

(extend-type URI
  Compile
  (compile [uri]
    (compile (File. uri)))
  Render
  (render [uri template data bundle]
    (render (File. uri) template data bundle)))

(extend-type URL
  Compile
  (compile [url]
    (compile (.toURI url)))
  Render
  (render [url template data bundle]
    (render (.toURI url) template data bundle)))
