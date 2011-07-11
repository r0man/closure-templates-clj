(ns closure.templates.fileset
  (:refer-clojure :exclude (replace))
  (:import [com.google.template.soy SoyFileSet$Builder] java.io.File)
  (:use [clojure.contrib.def :only (defvar)]
        [clojure.contrib.string :only (as-str)]
        [clojure.string :only (blank? replace)]
        [clojure.walk :only (postwalk stringify-keys)]
        [inflections.core :only (camelize underscore)]
        closure.templates.classpath))

(defvar *fileset* (ref #{})
  "A reference to the compiled Soy files.")

(defn add-soy-file!
  "Add the Soy file to *fileset*."
  [file] {:pre [(.isFile file)]}
  (dosync (alter *fileset* conj file)))

(defn clear-fileset!
  "Remove all Soy files from *fileset*."
  [] (dosync (ref-set *fileset* #{})))

(defn compile-fileset
  "Compile the Soy fileset into a Tofu object."
  [fileset]
  (let [builder (SoyFileSet$Builder.)]
    (doall (map #(.add builder %) fileset))
    (.compileToJavaObj (.build builder))))
