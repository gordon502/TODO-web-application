package io.github.mat3e.hello;

import io.github.mat3e.hello.HelloService;
import io.github.mat3e.lang.Lang;
import io.github.mat3e.lang.LangRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {

    private final static String WELCOME = "Hello";
    private final static String FALLBACK_ID_WELCOME = "Hola";

    @Test
    public void test_prepareGreeting_nullName_returnsGreetingWithFallback() {
        //given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnsGreetingWithName() {
        //given
        var SUT = new HelloService();
        String name = "test";

        //when
        var result = SUT.prepareGreeting(name, -1);

        //then
        assertEquals("Hello " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() {
        //given
        var mockRepository = fallBackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    /*@Test
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() {
        //given
        var mockRepository = fallBackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }*/

    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() {
        //given
        var mockRepository = new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository alwaysReturningHelloRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELCOME, null));
            }
        };
    }

    private LangRepository fallBackLangIdRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())){
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }
}