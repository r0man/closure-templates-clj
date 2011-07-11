(ns closure.templates.test
  (:import java.io.File)
  (:use closure.templates.classpath))

(def example-soy "soy/closure/templates/test/core.soy")
(def example-path (str "resources/" example-soy))
(def example-file (File. example-path))
(def example-uri (classpath-uri example-soy))
(def example-url (classpath-url example-soy))

