import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;


public interface CommandLine {


    @Option(defaultValue = "10",shortName = "n")

    int getNumber();

    @Option(shortName = "e")
    String getEntity();

    @Option(shortName = "r")
    boolean isReverse();

    @Option(shortName = "i")
    boolean isIgnore();

    @Unparsed

    String fileName();

}
