(ns closure.templates.tofu
  (:import com.google.template.soy.tofu.SoyTofu)
  (:use [clojure.contrib.def :only (defvar)]))

(defvar *tofu* (ref nil)
  "The global Tofu object that contains all compiled Soy template
  files that were define via deftemplate.")

(defn tofu?
  "Returns true if arg is a SoyTofu instance, otherwise false."
  [arg] (isa? (class arg) SoyTofu))