(ns #^{:doc "Rendering of Soy templates." :author "Roman Scherer"}
  closure.templates.render
  (:refer-clojure :exclude (compile))
  (:import java.io.File com.google.template.soy.tofu.SoyTofu)
  (:use [inflections.core :only (underscore-keys stringify-keys)]
        [inflections.transform :only (transform-values)]
        closure.templates.compile))

(defn transform-data [data]
  (-> data
      (transform-values #(if (isa? (class %) Long) (Integer. %) %))
      (stringify-keys)
      (underscore-keys)))

(defprotocol Render
  (render [object template data bundle]
    "Render the Soy template."))

(extend-type nil
  Render
  (render [_ _ _ _]
    nil))

(extend-type Object
  Render
  (render [file template data bundle]
    (render (compile file) template data bundle)))

(extend-type SoyTofu
  Render
  (render [tofu template data bundle]
    (.render tofu template (transform-data data) bundle)))
