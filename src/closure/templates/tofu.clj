(ns #^{:doc "Tofu helpers." :author "Roman Scherer"}
  closure.templates.tofu
  (:refer-clojure :exclude (compile))
  (:import com.google.template.soy.tofu.SoyTofu)
  (:use closure.templates.compile
        closure.templates.fileset))

(def ^:dynamic *tofu* (ref nil))

(defn compile!
  "Compile all templates in *fileset* and set *tofu* to the returned
  SoyTofu object."
  [] (dosync (ref-set *tofu* (compile @*fileset*))))

(defn tofu?
  "Returns true if arg is a SoyTofu instance, otherwise false."
  [arg] (isa? (class arg) SoyTofu))