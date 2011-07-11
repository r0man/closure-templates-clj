(ns closure.templates.core
  (:refer-clojure :exclude (compile replace))
  (:use [clojure.contrib.def :only (defvar)]
        closure.templates.classpath
        closure.templates.fileset
        closure.templates.protocol
        closure.templates.soy))

(defvar *tofu* (ref nil)
  "The global Tofu object that contains all compiled Soy template
  files that were define via deftemplate.")

(defn compile-template
  "Compile arg into a SoyTofu object."
  [arg] (compile arg))

;; (defn compile-template!
;;   "Compile arg into a SoyTofu object and add it to the *tofu* object."
;;   [arg] (add-soy-file! (compile arg)))

(defn recompile!
  "Recompile the *tofu* object with the templates in *fileset*."
  [] (dosync (ref-set *tofu* (compile-template @*fileset*))))

(defn render-template
  "Render template using the compilation unit, interpolate the result
  with data and the optional message bundle."
  [compilation-unit template data & [bundle]]
  (render compilation-unit template data bundle))

(defmacro deftemplate [fn-name args body & {:keys [filename namespace]}]
  (let [fn-name# fn-name template# (template-name (str (or namespace *ns*) "/" fn-name#))]
    (add-soy-file! (classpath-file (template-path (str (or namespace *ns*) "/" fn-name#))))
    (recompile!)
    (prn (template-path (str (or namespace *ns*) "/" fn-name#)))
    (prn template#)
    ;; TODO: How to get the correct namespace of the caller?
    `(defn ~fn-name# [~@args]
       (render-template @*tofu* ~template# (do ~body)))))
