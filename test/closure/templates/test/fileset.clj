(ns closure.templates.test.fileset
  (:import java.io.File)
  (:use clojure.test
        closure.templates.test
        closure.templates.soy
        closure.templates.tofu
        closure.templates.fileset))

(deftest test-add-soy!
  (let [file (soy example-path)]
    (clear-fileset!)
    (add-soy! file)
    (is (= 1 (count @*fileset*)))
    (is (contains? @*fileset* file))))

(deftest test-clear-fileset!
  (add-soy! (soy example-path))
  (clear-fileset!)
  (is (empty? @*fileset*)))

(deftest test-compile-fileset
  (is (tofu? (compile-fileset (soy-seq "resources/soy")))))
