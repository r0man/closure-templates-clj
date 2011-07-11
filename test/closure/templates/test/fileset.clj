(ns closure.templates.test.fileset
  (:import [com.google.template.soy.tofu SoyTofu]
           java.io.File)
  (:use clojure.test
        closure.templates.test
        closure.templates.soy
        closure.templates.fileset))

(deftest test-add-soy-file!
  (let [file (soy-file example-path)]
    (clear-fileset!)
    (add-soy-file! file)
    (is (= 1 (count @*fileset*)))
    (is (contains? @*fileset* file))))

(deftest test-clear-fileset!
  (add-soy-file! (soy-file example-path))
  (clear-fileset!)
  (is (empty? @*fileset*)))

(deftest test-compile-fileset
  (let [tofu (compile-fileset (soy-file-seq "resources/soy"))]
    (is (isa? (class tofu) SoyTofu))))
