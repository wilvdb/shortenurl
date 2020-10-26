# Shorten URL

It aims to test numerous frameworks:
* Testing framework (JUnit5, Spock...)
* Assertions libraries (Hamcrest, AssertJ, RestAssured)
* Mock framework (Mockito, KMock, WireMock)

# JUnit 5

## Step 1

Open ```UrlShortenServiceTest``` and ```UrlShortenServiceShortenFailureJ4Test``` and for each, apply as described below:
* Remove ```public``` declaration for class and methods
* Move from ```org.junit.Test``` to ```org.junit.jupiter.api.Test```
* Replace ```@Before``` by ```@BeforeEach```
* Replace ```@After``` by ```@AfterEach```
* Replace ```org.junit.Assert``` by ```org.junit.jupiter.api.Assertions```

## Step 2

Open ```UrlShortenServiceShortenFailureJ4Test``` and apply as described below:
* Replace ```try/catch``` mechanism by ```assertThrows```
* Encapsulate the two last assertions with ```assertAll```
* Copy test class in ```UrlShortenServiceTest``` by nesting it

## Step 3

Open ```UrlShortenServiceShortenUrlStrategyJ4Test``` and apply as described below:
* Refactor parameterized test by JUnit5 ```@EnumSource```

## Step 4

Open ```UrlRepositoryTest``` and apply as described below:
* Apply step 1 on this class
* Move from JUnit 4 runner to JUnit 5 extension

# Assertions

## Hamcrest

Replace JUnit5 assertions by Hamcrest on ```UrlShortenServiceTest```.

Applying several assertions at once is not possible. Only applying several matchers to one value is possible.

Does not support specific assertions for exceptions.

## AssertJ

Replace JUnit5 assertions by AssertJ on ```UrlShortenServiceTest```.
