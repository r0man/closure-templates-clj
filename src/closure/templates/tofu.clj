(ns closure.templates.tofu
  (:import com.google.template.soy.tofu.SoyTofu))

(defn tofu?
  "Returns true if arg is a SoyTofu instance, otherwise false."
  [arg] (isa? (class arg) SoyTofu))