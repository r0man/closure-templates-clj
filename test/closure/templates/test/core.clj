(ns closure.templates.test.core
  (:use closure.templates.core
        clojure.test))

(deftemplate hello-name [name]
  {:name name})

(deftest test-hello-name
  (is (= "Hello closure!" (hello-name "closure"))))




