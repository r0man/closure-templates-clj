(ns closure.templates.render
  (:refer-clojure :exclude (compile))
  (:import java.io.File com.google.template.soy.tofu.SoyTofu)
  (:use [clojure.walk :only (stringify-keys)]
        [inflections.core :only (underscore-keys)]
        closure.templates.compile))

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
    (.render tofu template (underscore-keys (stringify-keys data)) bundle)))
