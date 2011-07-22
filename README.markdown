
# closure-templates-clj

Clojure library for Google's Closure Templates.

## Installation

Via [Clojars](http://clojars.org/closure-templates-clj).

## Documentation

The API documentation is available [here](http://r0man.github.com/closure-templates-clj).

## Examples
<pre> <code>
(ns closure.templates.test.core
  (:use closure.templates.core))

(deftemplate hello-name [name]
  {:name name})

(hello-name \"Closure\")
;=> \"Hello Closure\"
</pre> </code>

## License

Copyright (C) 2011 Roman Scherer

Distributed under the Eclipse Public License, the same as Clojure.
