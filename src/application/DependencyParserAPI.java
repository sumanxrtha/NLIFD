package application;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.*;

public class DependencyParserAPI {

    /* this codes are imported from official website of stanford coreNLP api and modified.
     * representation of coreNLP with stanford NLP api and genearting wordlist
     * for given question.
     * DependencyGeneartion method provides the required worllist of user input sql question.
     * */

//    public static void main(String []args) throws Exception {
//        String text = "A quick brown fox jumped over the lazy dog.";
//        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//        LexicalizedParser lp = LexicalizedParser.loadModel();
//        lp.setOptionFlags(new String[]{"-maxLength", "500", "-retainTmpSubcategories"});
//        TokenizerFactory<CoreLabel> tokenizerFactory =
//                PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
//        List<CoreLabel> wordList = tokenizerFactory.getTokenizer(new StringReader(text)).tokenize();
//        Tree tree = lp.apply(wordList);
//        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//        Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed(true);
//    }

    // generatelist takes string or question from user input
    // returning list to the collection coll type.
    @SuppressWarnings("rawtypes")
    // input == question from QueryPanelController
    public static Collection DependencyGeneration(String input) {
        Collection Coll;
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();

        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");

        lp.setOptionFlags(new String[]{"-maxLength", "500", "-retainTmpSubcategories"});

        TokenizerFactory tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        List wordList = tokenizerFactory.getTokenizer(new StringReader(input)).tokenize();

        @SuppressWarnings("unchecked")
        Tree tree = lp.apply(wordList);

        GrammaticalStructure gs = gsf.newGrammaticalStructure((edu.stanford.nlp.trees.Tree) tree);
        Coll = gs.typedDependenciesCollapsed(true);
        //collapsedDependencies
        return Coll; // collections return

//        System.out.println(coll);
    }


}