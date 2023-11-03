(ns example1
  (:require [tablecloth.api :as tc]
            [scicloj.ml.dataset :as ds]
            [scicloj.ml.core :as ml]
            [scicloj.ml.metamorph :as mm]
            [scicloj.ml.dataset :refer [dataset add-column] :as ds]
            [scicloj.noj.v1.datasets :as datasets]
            [scicloj.noj.v1.vis :as vis]
            [aerial.hanami.templates :as ht]
            [aerial.hanami.common :as hc]))

datasets/iris

(def iris-train-and-test
  (-> datasets/iris
      (ds/train-test-split {:seed 1})))

iris-train-and-test

(def plot-spec
  {:X "sepal-length"
   :Y "sepal-width"
   :COLOR "species"
   :MSIZE 100
   :XSCALE {:zero false}
   :YSCALE {:zero false}})

(-> iris-train-and-test
    :train-ds
    (vis/hanami-plot ht/point-chart
                     plot-spec))

(def pipe-fn
  (ml/pipeline
   (mm/select-columns [:sepal-length :sepal-width :species])
   (mm/categorical->number [:species])
   (mm/set-inference-target :species)
   (mm/model {:model-type :smile.classification/logistic-regression})))

(def trained-ctx
  (pipe-fn {:metamorph/data (:train-ds iris-train-and-test)
            :metamorph/mode :fit}))

(def test-ctx
  (pipe-fn
   (assoc trained-ctx
          :metamorph/data (:test-ds iris-train-and-test)
          :metamorph/mode :transform)))

(def test-predictions
  (-> test-ctx
      :metamorph/data
      (ds/column-values->categorical :species)))

(def real-test-species
  (-> iris-train-and-test
      :test-ds
      :species))

(-> (ml/confusion-map test-predictions
                      real-test-species)
    ml/confusion-map->ds)


{:test-real
 (-> iris-train-and-test
     :test-ds
     (vis/hanami-plot ht/point-chart
                      plot-spec))
 :test-pred
 (-> iris-train-and-test
     :test-ds
     (tc/add-column :prediction test-predictions)
     (vis/hanami-plot ht/point-chart
                      (assoc plot-spec
                             :COLOR "prediction")))}
