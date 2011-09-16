(defproject closure-templates-clj "0.0.2-SNAPSHOT"
  :author "Roman Scherer"
  :autodoc {:copyright "Copyright (c) 2011 Roman Scherer"
            :name "Clojure Library for Google's Closure Templates"
            :web-src-dir "https://github.com/r0man/closure-templates-clj/blob/"
            :web-home "https://r0man.github.com/closure-templates-clj/"}
  :description "Clojure Library for Google's Closure Templates."
  :url "https://github.com/r0man/closure-templates-clj"
  :dependencies [[clj-soy/google-closure-templates "20100708"]
                 [inflections "0.4.4"]
                 [org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]]
  :dev-dependencies [[org.clojars.rayne/autodoc "0.8.0-SNAPSHOT"]])
