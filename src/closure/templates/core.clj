(ns closure.templates.core
  (:refer-clojure :exclude (replace))
  (:use [clojure.contrib.def :only (defvar)]
        closure.templates.classpath
        closure.templates.resources))

(defvar *tofu* (ref nil)
  "The Tofu object that contains all compiled Soy template files.")

(defmacro deftemplate [name args body & {:keys [filename namespace]}]
  (let [name# name
        template-name# (template-name (str (or namespace *ns*) "/" name#))
        template-path# (template-path (str (or namespace *ns*) "/" name#))]
    (add-soy-file! (classpath-file template-path#))
    (dosync (ref-set *tofu* (compile-fileset @*fileset*)))
    `(defn ~name [~@args]
       (render-template @*tofu* ~template-name# (do ~body)))))
