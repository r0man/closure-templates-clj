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
  "Define a render fn for a Soy template. The macro expects that the
Soy templates are defined in files matching the hierarchy of the
Clojure namespace in the Soy *directory* on the classpath.

The render fn \"hello-name\" defined in the namespace
\"closure.templates.test.core\" expects a Soy template with the name
\".helloName\" in the \"soy/closure/templates/test/core.soy\" file on
the classpath.

Example:

  (ns closure.templates.test.core
    (:use closure.templates.core))

  (deftemplate hello-name [name]
    {:name name})

  (hello-name \"Closure\")
  ;=> \"Hello Closure\"
"
  [name args body & {:keys [filename namespace]}]
  (let [name# name namespace# (or namespace *ns*)]
    `(do
       (add-soy! (resource ~(fn-path name# namespace#)))
       (compile!)
       (defn ~name# [~@args]
         (render-template @*tofu* ~(fn-js name# namespace#) (do ~body))))))
