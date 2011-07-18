(ns closure.templates.fileset
  (:import [com.google.template.soy SoyFileSet$Builder])
  (:use [clojure.contrib.def :only (defvar)]))

(defvar *fileset* (ref #{})
  "A reference to the compiled Soy files.")

(defn add-soy-file!
  "Add the Soy file to *fileset*."
  [file]
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
