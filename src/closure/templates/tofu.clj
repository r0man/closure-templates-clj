(ns #^{:doc "Tofu helpers." :author "Roman Scherer"}
  closure.templates.tofu
  (:refer-clojure :exclude (compile))
  (:import com.google.template.soy.tofu.SoyTofu)
  (:use [clojure.contrib.def :only (defvar)]
        closure.templates.compile
        closure.templates.fileset))

(defvar *tofu* (ref nil)
  "The global Tofu object that contains all compiled Soy template
  files that were define via deftemplate.")

(defn compile!
  "Compile all templates in *fileset* and set *tofu* to the returned
  SoyTofu object."
  [] (dosync (ref-set *tofu* (compile @*fileset*))))

(defn tofu?
  "Returns true if arg is a SoyTofu instance, otherwise false."
  [arg] (isa? (class arg) SoyTofu))