(ns closure.templates.test.tofu
  (:use clojure.test
        closure.templates.core
        closure.templates.test
        closure.templates.tofu))

(deftest test-tofu?
  (is (not (tofu? nil)))
  (is (not (tofu? "")))
  (is (tofu? (compile-template example-file))))
