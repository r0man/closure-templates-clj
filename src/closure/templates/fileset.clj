(ns #^{:doc "Fileset helpers." :author "Roman Scherer"}
  closure.templates.fileset
  (:import [com.google.template.soy SoyFileSet$Builder]))

(def ^:dynamic *fileset* (ref #{}))

(defn add-soy!
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
