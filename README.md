# chatgpt-deme

openai的api最新支持gpt-3.5-turbo聊天模式，该模式支持会后记忆，可以根据上下文回答问题。基本媲美chatgpt网页版
官方提供了python和nodejs的sdk。我基于[https://github.com/TheoKanning/openai-java](https://github.com/TheoKanning/openai-java)
这个开源的openai java sdk写了gpt-3.5-turbo的demo
## 运行demo
```shell
git clone https://github.com/nathanhex/chatgpt-demo.git
mvn package
java -jar target/chat-jar-with-dependencies.jar
```
国内目前已经无法直接调用openai的api需要自备机场，然后执行运行命令条件代理：
```shell
java -Dhttp.proxySet=true -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1082 -jar target/chat-jar-with-dependencies.jar
```
## openai参数说明：
+ 【max_tokens】integer 可选 默认值16
  完成时要生成的最大token数量。

提示的token计数加上max_tokens不能超过模型的上下文长度。大多数模型的上下文长度为2048个token（最新模型除外，支持4096个）。
+ 【temperature】number 可选 默认值1
  使用什么样的采样温度，介于0和2之间。较高的值（如0.8）将使输出更加随机，而较低的值（例如0.2）将使其更加集中和确定。

通常建议更改它或top_p，但不能同时更改两者。
+ 【top_p】number 可选 默认值1
  一种用温度采样的替代品，称为核采样，其中模型考虑了具有top_p概率质量的token的结果。因此，0.1意味着只考虑包含前10%概率质量的token。

通常建议改变它或temperature，但不能同时更改两者。

+ 【presence_penalty】number 可选 默认值0
  取值范围：-2.0~2.0。
  正值根据新token到目前为止是否出现在文本中来惩罚它们，这增加了模型谈论新主题的可能性。

+ 【frequency_penalty】number 可选 默认值0
取值范围：-2.0~2.0。
正值根据迄今为止文本中的现有频率惩罚新token，从而降低了模型逐字重复同一行的可能性。

+ 【best_of】integer 可选 默认值1
  在服务器端生成best_of个完成，并返回“最佳”（每个token的日志概率最高）。结果无法流式传输。

与n一起使用时，best_of控制候选完成的数量，n指定要返回的数量–best_of必须大于n。

注意：由于此参数会生成许多完成，因此它可以快速消耗token配额。小心使用并确保您对max_tokens和stop进行了合理的设置。

+ 【logit_bias】map 可选 默认值null
  修改完成时出现指定token的可能性。

接受将token（由其在GPT token生成器中的token ID指定）映射到从-100到100的相关偏差值的json对象。
您可以使用此token工具（适用于GPT-2和GPT-3）将文本转换为token ID。在数学上，偏差在采样之前被添加到模型生成的逻辑中。确切的效果因模型而异，但-1和1之间的值应该会降低或增加选择的可能性；-100或100这样的值应该导致相关token的禁止或独占选择。

例如，可以传递｛“50256”：-100｝以防止生成<|endoftext|>的token。