
---
format:
  html: {toc: true, toc-depth: 4, theme: spacelab, output-file: example1.html}
monofont: Fira Code Medium
highlight-style: solarized
code-block-background: true
include-in-header: {text: '<link rel = "icon" href = "data:," />'}

---
<style>table {
  border-style: thin;
}
th, td {
  padding: 6px;
}
td {
  text-align: left;
}
th {
  text-align: center;
  background-color: #ddd;
}
tr:nth-child(even) {
  background-color: #f6f6f6;
}
</style><style>.printedClojure .sourceCode {
  background-color: transparent;
  border-style: none;
}
</style><script src="https://code.jquery.com/jquery-3.6.0.min.js" type="text/javascript"></script><script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js" type="text/javascript"></script><script src="https://cdn.jsdelivr.net/npm/vega@5.22.1" type="text/javascript"></script><script src="https://cdn.jsdelivr.net/npm/vega-lite@5.6.0" type="text/javascript"></script><script src="https://cdn.jsdelivr.net/npm/vega-embed@6.21.0" type="text/javascript"></script>
<div class="sourceClojure">
```clojure
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
```
</div>



<div class="sourceClojure">
```clojure
datasets/iris
```
</div>


_unnamed [150 5]:

| :sepal-length | :sepal-width | :petal-length | :petal-width |  :species |
|--------------:|-------------:|--------------:|-------------:|-----------|
|           5.1 |          3.5 |           1.4 |          0.2 |    setosa |
|           4.9 |          3.0 |           1.4 |          0.2 |    setosa |
|           4.7 |          3.2 |           1.3 |          0.2 |    setosa |
|           4.6 |          3.1 |           1.5 |          0.2 |    setosa |
|           5.0 |          3.6 |           1.4 |          0.2 |    setosa |
|           5.4 |          3.9 |           1.7 |          0.4 |    setosa |
|           4.6 |          3.4 |           1.4 |          0.3 |    setosa |
|           5.0 |          3.4 |           1.5 |          0.2 |    setosa |
|           4.4 |          2.9 |           1.4 |          0.2 |    setosa |
|           4.9 |          3.1 |           1.5 |          0.1 |    setosa |
|           ... |          ... |           ... |          ... |       ... |
|           6.9 |          3.1 |           5.4 |          2.1 | virginica |
|           6.7 |          3.1 |           5.6 |          2.4 | virginica |
|           6.9 |          3.1 |           5.1 |          2.3 | virginica |
|           5.8 |          2.7 |           5.1 |          1.9 | virginica |
|           6.8 |          3.2 |           5.9 |          2.3 | virginica |
|           6.7 |          3.3 |           5.7 |          2.5 | virginica |
|           6.7 |          3.0 |           5.2 |          2.3 | virginica |
|           6.3 |          2.5 |           5.0 |          1.9 | virginica |
|           6.5 |          3.0 |           5.2 |          2.0 | virginica |
|           6.2 |          3.4 |           5.4 |          2.3 | virginica |
|           5.9 |          3.0 |           5.1 |          1.8 | virginica |




<div class="sourceClojure">
```clojure
(def iris-train-and-test
  (-> datasets/iris
      (ds/train-test-split {:seed 1})))
```
</div>



<div class="sourceClojure">
```clojure
iris-train-and-test
```
</div>


<div><p>{</p><div style="margin-left:10%;width:110%;"><table><tr><td valign="top"><div><pre><code class="language-clojure">:train-ds
</code></pre></div></td><td><div style="margin-top:10px;"><div><p>_unnamed \[105 5\]:</p><table><thead><tr><th style="text-align:right;">:sepal-length</th><th style="text-align:right;">:sepal-width</th><th style="text-align:right;">:petal-length</th><th style="text-align:right;">:petal-width</th><th>:species</th></tr></thead><tbody><tr><td style="text-align:right;">6.7</td><td style="text-align:right;">3.1</td><td style="text-align:right;">5.6</td><td style="text-align:right;">2.4</td><td>virginica</td></tr><tr><td style="text-align:right;">6.7</td><td style="text-align:right;">3.0</td><td style="text-align:right;">5.2</td><td style="text-align:right;">2.3</td><td>virginica</td></tr><tr><td style="text-align:right;">5.6</td><td style="text-align:right;">2.9</td><td style="text-align:right;">3.6</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">4.6</td><td style="text-align:right;">3.1</td><td style="text-align:right;">1.5</td><td style="text-align:right;">0.2</td><td>setosa</td></tr><tr><td style="text-align:right;">5.9</td><td style="text-align:right;">3.2</td><td style="text-align:right;">4.8</td><td style="text-align:right;">1.8</td><td>versicolor</td></tr><tr><td style="text-align:right;">7.6</td><td style="text-align:right;">3.0</td><td style="text-align:right;">6.6</td><td style="text-align:right;">2.1</td><td>virginica</td></tr><tr><td style="text-align:right;">5.5</td><td style="text-align:right;">2.4</td><td style="text-align:right;">3.7</td><td style="text-align:right;">1.0</td><td>versicolor</td></tr><tr><td style="text-align:right;">6.4</td><td style="text-align:right;">2.8</td><td style="text-align:right;">5.6</td><td style="text-align:right;">2.2</td><td>virginica</td></tr><tr><td style="text-align:right;">5.4</td><td style="text-align:right;">3.9</td><td style="text-align:right;">1.7</td><td style="text-align:right;">0.4</td><td>setosa</td></tr><tr><td style="text-align:right;">6.8</td><td style="text-align:right;">3.2</td><td style="text-align:right;">5.9</td><td style="text-align:right;">2.3</td><td>virginica</td></tr><tr><td style="text-align:right;">...</td><td style="text-align:right;">...</td><td style="text-align:right;">...</td><td style="text-align:right;">...</td><td>...</td></tr><tr><td style="text-align:right;">6.4</td><td style="text-align:right;">3.1</td><td style="text-align:right;">5.5</td><td style="text-align:right;">1.8</td><td>virginica</td></tr><tr><td style="text-align:right;">7.7</td><td style="text-align:right;">3.8</td><td style="text-align:right;">6.7</td><td style="text-align:right;">2.2</td><td>virginica</td></tr><tr><td style="text-align:right;">6.3</td><td style="text-align:right;">2.9</td><td style="text-align:right;">5.6</td><td style="text-align:right;">1.8</td><td>virginica</td></tr><tr><td style="text-align:right;">5.6</td><td style="text-align:right;">2.7</td><td style="text-align:right;">4.2</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">5.7</td><td style="text-align:right;">4.4</td><td style="text-align:right;">1.5</td><td style="text-align:right;">0.4</td><td>setosa</td></tr><tr><td style="text-align:right;">6.9</td><td style="text-align:right;">3.1</td><td style="text-align:right;">4.9</td><td style="text-align:right;">1.5</td><td>versicolor</td></tr><tr><td style="text-align:right;">7.4</td><td style="text-align:right;">2.8</td><td style="text-align:right;">6.1</td><td style="text-align:right;">1.9</td><td>virginica</td></tr><tr><td style="text-align:right;">6.6</td><td style="text-align:right;">2.9</td><td style="text-align:right;">4.6</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">5.0</td><td style="text-align:right;">3.2</td><td style="text-align:right;">1.2</td><td style="text-align:right;">0.2</td><td>setosa</td></tr><tr><td style="text-align:right;">6.3</td><td style="text-align:right;">3.3</td><td style="text-align:right;">6.0</td><td style="text-align:right;">2.5</td><td>virginica</td></tr><tr><td style="text-align:right;">6.3</td><td style="text-align:right;">2.3</td><td style="text-align:right;">4.4</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr></tbody></table></div></div></td></tr></table><table><tr><td valign="top"><div><pre><code class="language-clojure">:test-ds
</code></pre></div></td><td><div style="margin-top:10px;"><div><p>_unnamed \[45 5\]:</p><table><thead><tr><th style="text-align:right;">:sepal-length</th><th style="text-align:right;">:sepal-width</th><th style="text-align:right;">:petal-length</th><th style="text-align:right;">:petal-width</th><th>:species</th></tr></thead><tbody><tr><td style="text-align:right;">4.5</td><td style="text-align:right;">2.3</td><td style="text-align:right;">1.3</td><td style="text-align:right;">0.3</td><td>setosa</td></tr><tr><td style="text-align:right;">5.0</td><td style="text-align:right;">3.4</td><td style="text-align:right;">1.5</td><td style="text-align:right;">0.2</td><td>setosa</td></tr><tr><td style="text-align:right;">6.2</td><td style="text-align:right;">2.9</td><td style="text-align:right;">4.3</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">6.4</td><td style="text-align:right;">3.2</td><td style="text-align:right;">5.3</td><td style="text-align:right;">2.3</td><td>virginica</td></tr><tr><td style="text-align:right;">5.7</td><td style="text-align:right;">2.8</td><td style="text-align:right;">4.5</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">6.4</td><td style="text-align:right;">2.8</td><td style="text-align:right;">5.6</td><td style="text-align:right;">2.1</td><td>virginica</td></tr><tr><td style="text-align:right;">5.2</td><td style="text-align:right;">3.5</td><td style="text-align:right;">1.5</td><td style="text-align:right;">0.2</td><td>setosa</td></tr><tr><td style="text-align:right;">5.7</td><td style="text-align:right;">2.9</td><td style="text-align:right;">4.2</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">6.1</td><td style="text-align:right;">2.9</td><td style="text-align:right;">4.7</td><td style="text-align:right;">1.4</td><td>versicolor</td></tr><tr><td style="text-align:right;">6.0</td><td style="text-align:right;">2.2</td><td style="text-align:right;">5.0</td><td style="text-align:right;">1.5</td><td>virginica</td></tr><tr><td style="text-align:right;">...</td><td style="text-align:right;">...</td><td style="text-align:right;">...</td><td style="text-align:right;">...</td><td>...</td></tr><tr><td style="text-align:right;">5.5</td><td style="text-align:right;">2.5</td><td style="text-align:right;">4.0</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">4.3</td><td style="text-align:right;">3.0</td><td style="text-align:right;">1.1</td><td style="text-align:right;">0.1</td><td>setosa</td></tr><tr><td style="text-align:right;">5.8</td><td style="text-align:right;">2.8</td><td style="text-align:right;">5.1</td><td style="text-align:right;">2.4</td><td>virginica</td></tr><tr><td style="text-align:right;">5.7</td><td style="text-align:right;">2.5</td><td style="text-align:right;">5.0</td><td style="text-align:right;">2.0</td><td>virginica</td></tr><tr><td style="text-align:right;">7.0</td><td style="text-align:right;">3.2</td><td style="text-align:right;">4.7</td><td style="text-align:right;">1.4</td><td>versicolor</td></tr><tr><td style="text-align:right;">6.4</td><td style="text-align:right;">2.9</td><td style="text-align:right;">4.3</td><td style="text-align:right;">1.3</td><td>versicolor</td></tr><tr><td style="text-align:right;">5.5</td><td style="text-align:right;">2.6</td><td style="text-align:right;">4.4</td><td style="text-align:right;">1.2</td><td>versicolor</td></tr><tr><td style="text-align:right;">5.1</td><td style="text-align:right;">3.5</td><td style="text-align:right;">1.4</td><td style="text-align:right;">0.2</td><td>setosa</td></tr><tr><td style="text-align:right;">6.4</td><td style="text-align:right;">2.7</td><td style="text-align:right;">5.3</td><td style="text-align:right;">1.9</td><td>virginica</td></tr><tr><td style="text-align:right;">6.3</td><td style="text-align:right;">2.5</td><td style="text-align:right;">4.9</td><td style="text-align:right;">1.5</td><td>versicolor</td></tr><tr><td style="text-align:right;">7.7</td><td style="text-align:right;">3.0</td><td style="text-align:right;">6.1</td><td style="text-align:right;">2.3</td><td>virginica</td></tr></tbody></table></div></div></td></tr></table></div><p>}</p></div>


<div class="sourceClojure">
```clojure
(def plot-spec
  {:X "sepal-length"
   :Y "sepal-width"
   :COLOR "species"
   :MSIZE 100
   :XSCALE {:zero false}
   :YSCALE {:zero false}})
```
</div>



<div class="sourceClojure">
```clojure
(-> iris-train-and-test
    :train-ds
    (vis/hanami-plot ht/point-chart
                     plot-spec))
```
</div>


<div><script>vegaEmbed(document.currentScript.parentElement, {"encoding":{"y":{"scale":{"zero":false},"field":"sepal-width","type":"quantitative"},"color":{"field":"species","type":"nominal"},"x":{"scale":{"zero":false},"field":"sepal-length","type":"quantitative"}},"mark":{"type":"circle","size":100,"tooltip":true},"width":400,"background":"floralwhite","height":300,"data":{"values":"sepal-length,sepal-width,petal-length,petal-width,species\n6.7,3.1,5.6,2.4,virginica\n6.7,3.0,5.2,2.3,virginica\n5.6,2.9,3.6,1.3,versicolor\n4.6,3.1,1.5,0.2,setosa\n5.9,3.2,4.8,1.8,versicolor\n7.6,3.0,6.6,2.1,virginica\n5.5,2.4,3.7,1.0,versicolor\n6.4,2.8,5.6,2.2,virginica\n5.4,3.9,1.7,0.4,setosa\n6.8,3.2,5.9,2.3,virginica\n6.7,3.0,5.0,1.7,versicolor\n5.5,3.5,1.3,0.2,setosa\n4.6,3.4,1.4,0.3,setosa\n6.2,2.8,4.8,1.8,virginica\n4.9,3.0,1.4,0.2,setosa\n5.7,2.6,3.5,1.0,versicolor\n6.5,2.8,4.6,1.5,versicolor\n4.9,3.6,1.4,0.1,setosa\n5.4,3.4,1.5,0.4,setosa\n4.6,3.2,1.4,0.2,setosa\n6.5,3.0,5.5,1.8,virginica\n6.6,3.0,4.4,1.4,versicolor\n5.6,3.0,4.5,1.5,versicolor\n5.0,3.5,1.3,0.3,setosa\n7.1,3.0,5.9,2.1,virginica\n5.9,3.0,5.1,1.8,virginica\n4.6,3.6,1.0,0.2,setosa\n4.8,3.0,1.4,0.3,setosa\n5.0,3.5,1.6,0.6,setosa\n5.2,3.4,1.4,0.2,setosa\n7.2,3.2,6.0,1.8,virginica\n5.1,2.5,3.0,1.1,versicolor\n6.0,2.7,5.1,1.6,versicolor\n6.1,2.8,4.0,1.3,versicolor\n5.4,3.9,1.3,0.4,setosa\n5.1,3.7,1.5,0.4,setosa\n6.7,3.1,4.7,1.5,versicolor\n5.2,2.7,3.9,1.4,versicolor\n5.8,2.7,3.9,1.2,versicolor\n5.8,2.6,4.0,1.2,versicolor\n6.2,2.2,4.5,1.5,versicolor\n5.6,2.5,3.9,1.1,versicolor\n6.3,2.7,4.9,1.8,virginica\n6.1,2.8,4.7,1.2,versicolor\n5.0,3.3,1.4,0.2,setosa\n7.7,2.6,6.9,2.3,virginica\n5.1,3.3,1.7,0.5,setosa\n7.3,2.9,6.3,1.8,virginica\n6.7,3.1,4.4,1.4,versicolor\n6.8,2.8,4.8,1.4,versicolor\n5.7,3.8,1.7,0.3,setosa\n6.1,3.0,4.9,1.8,virginica\n6.9,3.2,5.7,2.3,virginica\n6.1,3.0,4.6,1.4,versicolor\n5.0,3.0,1.6,0.2,setosa\n5.5,2.3,4.0,1.3,versicolor\n5.8,2.7,5.1,1.9,virginica\n5.7,2.8,4.1,1.3,versicolor\n4.9,2.5,4.5,1.7,virginica\n5.6,2.8,4.9,2.0,virginica\n6.0,3.0,4.8,1.8,virginica\n5.1,3.5,1.4,0.3,setosa\n5.8,2.7,5.1,1.9,virginica\n4.7,3.2,1.3,0.2,setosa\n5.1,3.4,1.5,0.2,setosa\n5.5,4.2,1.4,0.2,setosa\n6.5,3.2,5.1,2.0,virginica\n4.7,3.2,1.6,0.2,setosa\n6.4,3.2,4.5,1.5,versicolor\n5.3,3.7,1.5,0.2,setosa\n6.7,3.3,5.7,2.5,virginica\n4.9,3.1,1.5,0.1,setosa\n6.9,3.1,5.4,2.1,virginica\n4.4,2.9,1.4,0.2,setosa\n4.8,3.0,1.4,0.1,setosa\n5.4,3.7,1.5,0.2,setosa\n4.8,3.4,1.9,0.2,setosa\n5.9,3.0,4.2,1.5,versicolor\n5.1,3.8,1.6,0.2,setosa\n5.1,3.8,1.5,0.3,setosa\n5.4,3.4,1.7,0.2,setosa\n6.7,2.5,5.8,1.8,virginica\n5.0,2.0,3.5,1.0,versicolor\n5.5,2.4,3.8,1.1,versicolor\n5.6,3.0,4.1,1.3,versicolor\n7.2,3.0,5.8,1.6,virginica\n7.7,2.8,6.7,2.0,virginica\n5.0,2.3,3.3,1.0,versicolor\n6.8,3.0,5.5,2.1,virginica\n6.3,2.5,5.0,1.9,virginica\n5.0,3.6,1.4,0.2,setosa\n6.1,2.6,5.6,1.4,virginica\n7.2,3.6,6.1,2.5,virginica\n6.0,2.2,4.0,1.0,versicolor\n6.4,3.1,5.5,1.8,virginica\n7.7,3.8,6.7,2.2,virginica\n6.3,2.9,5.6,1.8,virginica\n5.6,2.7,4.2,1.3,versicolor\n5.7,4.4,1.5,0.4,setosa\n6.9,3.1,4.9,1.5,versicolor\n7.4,2.8,6.1,1.9,virginica\n6.6,2.9,4.6,1.3,versicolor\n5.0,3.2,1.2,0.2,setosa\n6.3,3.3,6.0,2.5,virginica\n6.3,2.3,4.4,1.3,versicolor\n","format":{"type":"csv"}}});</script></div>


<div class="sourceClojure">
```clojure
(def pipe-fn
  (ml/pipeline
   (mm/select-columns [:sepal-length :sepal-width :species])
   (mm/categorical->number [:species])
   (mm/set-inference-target :species)
   (mm/model {:model-type :smile.classification/logistic-regression})))
```
</div>



<div class="sourceClojure">
```clojure
(def trained-ctx
  (pipe-fn {:metamorph/data (:train-ds iris-train-and-test)
            :metamorph/mode :fit}))
```
</div>



<div class="sourceClojure">
```clojure
(def test-ctx
  (pipe-fn
   (assoc trained-ctx
          :metamorph/data (:test-ds iris-train-and-test)
          :metamorph/mode :transform)))
```
</div>



<div class="sourceClojure">
```clojure
(def test-predictions
  (-> test-ctx
      :metamorph/data
      (ds/column-values->categorical :species)))
```
</div>



<div class="sourceClojure">
```clojure
(def real-test-species
  (-> iris-train-and-test
      :test-ds
      :species))
```
</div>



<div class="sourceClojure">
```clojure
(-> (ml/confusion-map test-predictions
                      real-test-species)
    ml/confusion-map->ds)
```
</div>


_unnamed [4 4]:

| :column-name | setosa | versicolor | virginica |
|--------------|--------|------------|-----------|
|  column-name | setosa | versicolor | virginica |
|       setosa | 0.9286 |    0.07143 |     0.000 |
|   versicolor |  0.000 |     0.7500 |    0.2500 |
|    virginica |  0.000 |     0.2000 |    0.8000 |




<div class="sourceClojure">
```clojure
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
```
</div>


<div><p>{</p><div style="margin-left:10%;width:110%;"><table><tr><td valign="top"><div><pre><code class="language-clojure">:test-real
</code></pre></div></td><td><div style="margin-top:10px;"><div><script>vegaEmbed(document.currentScript.parentElement, {"encoding":{"y":{"scale":{"zero":false},"field":"sepal-width","type":"quantitative"},"color":{"field":"species","type":"nominal"},"x":{"scale":{"zero":false},"field":"sepal-length","type":"quantitative"}},"mark":{"type":"circle","size":100,"tooltip":true},"width":400,"background":"floralwhite","height":300,"data":{"values":"sepal-length,sepal-width,petal-length,petal-width,species\n4.5,2.3,1.3,0.3,setosa\n5.0,3.4,1.5,0.2,setosa\n6.2,2.9,4.3,1.3,versicolor\n6.4,3.2,5.3,2.3,virginica\n5.7,2.8,4.5,1.3,versicolor\n6.4,2.8,5.6,2.1,virginica\n5.2,3.5,1.5,0.2,setosa\n5.7,2.9,4.2,1.3,versicolor\n6.1,2.9,4.7,1.4,versicolor\n6.0,2.2,5.0,1.5,virginica\n6.3,3.4,5.6,2.4,virginica\n5.8,2.7,4.1,1.0,versicolor\n6.2,3.4,5.4,2.3,virginica\n6.5,3.0,5.8,2.2,virginica\n6.0,3.4,4.5,1.6,versicolor\n6.5,3.0,5.2,2.0,virginica\n4.9,2.4,3.3,1.0,versicolor\n5.7,3.0,4.2,1.2,versicolor\n4.4,3.2,1.3,0.2,setosa\n7.9,3.8,6.4,2.0,virginica\n5.2,4.1,1.5,0.1,setosa\n6.0,2.9,4.5,1.5,versicolor\n5.1,3.8,1.9,0.4,setosa\n4.4,3.0,1.3,0.2,setosa\n6.3,3.3,4.7,1.6,versicolor\n6.9,3.1,5.1,2.3,virginica\n5.4,3.0,4.5,1.5,versicolor\n5.8,4.0,1.2,0.2,setosa\n4.8,3.1,1.6,0.2,setosa\n6.7,3.3,5.7,2.1,virginica\n5.0,3.4,1.6,0.4,setosa\n4.8,3.4,1.6,0.2,setosa\n6.3,2.8,5.1,1.5,virginica\n4.9,3.1,1.5,0.2,setosa\n5.5,2.5,4.0,1.3,versicolor\n4.3,3.0,1.1,0.1,setosa\n5.8,2.8,5.1,2.4,virginica\n5.7,2.5,5.0,2.0,virginica\n7.0,3.2,4.7,1.4,versicolor\n6.4,2.9,4.3,1.3,versicolor\n5.5,2.6,4.4,1.2,versicolor\n5.1,3.5,1.4,0.2,setosa\n6.4,2.7,5.3,1.9,virginica\n6.3,2.5,4.9,1.5,versicolor\n7.7,3.0,6.1,2.3,virginica\n","format":{"type":"csv"}}});</script></div></div></td></tr></table><table><tr><td valign="top"><div><pre><code class="language-clojure">:test-pred
</code></pre></div></td><td><div style="margin-top:10px;"><div><script>vegaEmbed(document.currentScript.parentElement, {"encoding":{"y":{"scale":{"zero":false},"field":"sepal-width","type":"quantitative"},"color":{"field":"prediction","type":"nominal"},"x":{"scale":{"zero":false},"field":"sepal-length","type":"quantitative"}},"mark":{"type":"circle","size":100,"tooltip":true},"width":400,"background":"floralwhite","height":300,"data":{"values":"sepal-length,sepal-width,petal-length,petal-width,species,prediction\n4.5,2.3,1.3,0.3,setosa,versicolor\n5.0,3.4,1.5,0.2,setosa,setosa\n6.2,2.9,4.3,1.3,versicolor,versicolor\n6.4,3.2,5.3,2.3,virginica,virginica\n5.7,2.8,4.5,1.3,versicolor,versicolor\n6.4,2.8,5.6,2.1,virginica,virginica\n5.2,3.5,1.5,0.2,setosa,setosa\n5.7,2.9,4.2,1.3,versicolor,versicolor\n6.1,2.9,4.7,1.4,versicolor,versicolor\n6.0,2.2,5.0,1.5,virginica,versicolor\n6.3,3.4,5.6,2.4,virginica,virginica\n5.8,2.7,4.1,1.0,versicolor,versicolor\n6.2,3.4,5.4,2.3,virginica,virginica\n6.5,3.0,5.8,2.2,virginica,virginica\n6.0,3.4,4.5,1.6,versicolor,virginica\n6.5,3.0,5.2,2.0,virginica,virginica\n4.9,2.4,3.3,1.0,versicolor,versicolor\n5.7,3.0,4.2,1.2,versicolor,versicolor\n4.4,3.2,1.3,0.2,setosa,setosa\n7.9,3.8,6.4,2.0,virginica,virginica\n5.2,4.1,1.5,0.1,setosa,setosa\n6.0,2.9,4.5,1.5,versicolor,versicolor\n5.1,3.8,1.9,0.4,setosa,setosa\n4.4,3.0,1.3,0.2,setosa,setosa\n6.3,3.3,4.7,1.6,versicolor,virginica\n6.9,3.1,5.1,2.3,virginica,virginica\n5.4,3.0,4.5,1.5,versicolor,versicolor\n5.8,4.0,1.2,0.2,setosa,setosa\n4.8,3.1,1.6,0.2,setosa,setosa\n6.7,3.3,5.7,2.1,virginica,virginica\n5.0,3.4,1.6,0.4,setosa,setosa\n4.8,3.4,1.6,0.2,setosa,setosa\n6.3,2.8,5.1,1.5,virginica,virginica\n4.9,3.1,1.5,0.2,setosa,setosa\n5.5,2.5,4.0,1.3,versicolor,versicolor\n4.3,3.0,1.1,0.1,setosa,setosa\n5.8,2.8,5.1,2.4,virginica,versicolor\n5.7,2.5,5.0,2.0,virginica,versicolor\n7.0,3.2,4.7,1.4,versicolor,virginica\n6.4,2.9,4.3,1.3,versicolor,virginica\n5.5,2.6,4.4,1.2,versicolor,versicolor\n5.1,3.5,1.4,0.2,setosa,setosa\n6.4,2.7,5.3,1.9,virginica,virginica\n6.3,2.5,4.9,1.5,versicolor,versicolor\n7.7,3.0,6.1,2.3,virginica,virginica\n","format":{"type":"csv"}}});</script></div></div></td></tr></table></div><p>}</p></div>

<div style="background-color:grey;height:2px;width:100%;"></div>

<div><pre><small><small>source: <a href="https://github.com/daslu/scicloj.ml-examples/blob/main//home/daslu/projects/daslu/scicloj.ml-example/notebooks/example1.clj">/home/daslu/projects/daslu/scicloj.ml-example/notebooks/example1.clj</a></small></small></pre></div>