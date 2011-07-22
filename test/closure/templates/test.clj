(ns closure.templates.test
  (:import java.io.File)
  (:use [clojure.java.io :only (resource)]))

(def example-soy "soy/closure/templates/test/core.soy")
(def example-path (str "resources/" example-soy))
(def example-file (File. example-path))
(def example-uri (.toURI (resource example-soy)))
(def example-url (resource example-soy))

