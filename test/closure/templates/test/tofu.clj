(ns closure.templates.test.tofu
  (:refer-clojure :exclude (compile))
  (:use clojure.test
        closure.templates.core
        closure.templates.test
        closure.templates.tofu))

(deftest test-tofu?
  (is (not (tofu? nil)))
  (is (not (tofu? "")))
  (is (tofu? (compile example-file))))
