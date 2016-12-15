(ns axcon.edn.serializer.core-test
  (:import [org.axonframework.serializer
            SimpleSerializedObject])
  (:require [clojure.test :refer :all]
            [axcon.edn.serializer.reader :as r]
            )
  (:use midje.sweet))

(fact "should get basic types"
      (r/read "a") =>  #'a
      (r/read "(:a :b)\n") => '(:a :b)
      )
