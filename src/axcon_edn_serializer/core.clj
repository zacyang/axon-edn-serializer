(ns axon.edn.serializer.core
  (:require [clojure.edn :as edn])
  (:import [org.axonframework.serializer
            Serializer
            SerializedObject
            SimpleSerializedObject
            SimpleSerializedType]
           [axcon-edn-serializer.core]))

(def ALL_SUPPORTED_TYPES #{clojure.lang.Keyword
                           })

;;;TODO add check wether the class has been imported or not
(defn import-by-name [fqn]
  (.importClass (the-ns *ns*)
                (clojure.lang.RT/classForName (str fqn))))

(defn gen-record
"input is symbol
generate namespace/map->"
  [tag]
  (let [tag_str (str tag)
        type_name (last (clojure.string/split tag_str #"\."))
        namespace_literal (clojure.string/replace tag_str  #"\.\w+$" "")]

    (resolve
     (symbol (str namespace_literal "/map->" type_name  )))))

(defn try-const
  [tag val]
  ;;(import org.apache.commons.codec.BinaryDecoder)
  ;;TODO generate record according to map with FQN

  (import-by-name (str tag))
                                        ;(eval (read-string (str "(map->" (str tag) ")")))
  ((gen-record tag) val))

(defn create-edn-serializer
  []
  (reify Serializer
    (serialize [_ obj expectedRep]
      (let [result (prn-str obj)]
        (SimpleSerializedObject.
         result
         java.lang.String
         (SimpleSerializedType. (str (type result)) "1"))))

    (canSerializeTo [_ expectedRep]
      (and  (not (nil? expectedRep))
            (contains? ALL_SUPPORTED_TYPES expectedRep)))

    (deserialize [_ sobj]
      (if (or (= java.lang.String  (.getContentType sobj))
              (string? (.getContentType sobj)))
     ;;  (tagged/read-string (.getData sobj))
           (edn/read-string {:default try-const} (.getData sobj))
        ;;(read-string (.getData sobj))
           ))


    (classForType [_ type])

    (typeForClass[_ type] )

    (getConverterFactory [_])

    ))
