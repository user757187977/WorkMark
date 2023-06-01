package demo.chat;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Scanner;

/**
 * @Description @Author lishoupeng @Date 2023/5/31 14:31
 */
public class ChatTest {

    private static final String TOKEN = System.getenv("OPENAI_API_KEY");
    private static final String MODEL = "text-davinci-003";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("请输入问题：");
        Scanner scanner = new Scanner(System.in);
        String prompt = scanner.nextLine();

        OpenAiService service = new OpenAiService(TOKEN, Duration.ofSeconds(10000));
        CompletionRequest completionRequest =
                CompletionRequest.builder()
                        // 使用的模型
                        .model(MODEL)
                        // 输入提示语
                        .prompt(prompt)
                        // 该值越大每次返回的结果越随机，即相似度越小，可选参数，默认值为 1，取值 0-2
                        .temperature(0.5)
                        // 返回结果最大分词数
                        .maxTokens(2048)
                        // 与temperature类似
                        .topP(1D)
                        .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        Thread.sleep(6000);
    }
}
