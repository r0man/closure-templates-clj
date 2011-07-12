(ns closure.templates.core
  (:refer-clojure :exclude (compile replace))
  (:use closure.templates.classpath
        closure.templates.compile
        closure.templates.fileset
        closure.templates.render
        closure.templates.soy
        closure.templates.tofu))

(defn compile-template
  "Compile arg into a SoyTofu object."
  [arg] (compile arg))

(defn recompile!
  "Recompile the *tofu* object with the templates in *fileset*."
  [] (dosync (ref-set *tofu* (compile-template (seq @*fileset*)))))

(defn render-template
  "Render template using the compilation unit, interpolate the result
  with data and the optional message bundle."
  [compilation-unit template data & [bundle]]
  (render compilation-unit template data bundle))

(defmacro deftemplate [fn-name args body & {:keys [filename namespace]}]
  (let [fn-name# fn-name
        template# (template-name fn-name# (or namespace *ns*))
        path# (template-path fn-name# (or namespace *ns*))]
    `(do
       (add-soy-file! (classpath-file ~path#))
       (recompile!)
       (defn ~fn-name# [~@args]
         (render-template @*tofu* ~template# (do ~body))))))
