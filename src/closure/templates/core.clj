(ns #^{:doc "Clojure library for Google's Closure Templates."
       :author "Roman Scherer"}
  closure.templates.core
  (:refer-clojure :exclude (compile))
  (:use [clojure.java.io :only (resource)]
        closure.templates.compile
        closure.templates.fileset
        closure.templates.render
        closure.templates.soy
        closure.templates.tofu))

(defn compile-template
  "Compile arg into a SoyTofu object."
  [arg] (compile arg))

(defn render-template
  "Render template using the tofu, interpolate the result with data
  and the optional message bundle."
  [tofu template data & [bundle]]
  (render tofu template data bundle))

(defmacro deftemplate
  "Define a Soy template."
  [fn-name args body & {:keys [filename namespace]}]
  (let [fn-name# fn-name namespace# (or namespace *ns*)]
    `(do
       (add-soy! (resource ~(fn-path fn-name# namespace#)))
       (compile!)
       (defn ~fn-name# [~@args]
         (render-template @*tofu* ~(fn-js fn-name# namespace#) (do ~body))))))
