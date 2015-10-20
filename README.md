# ProperRatingBar
Inspired by stock android RatingBar. Simpler, has features that original lacks.

## What is this about
Whatever you need to display some rating or pricing category - just use ProperRatingBar so this:
```xml
<io.techery.properratingbar.ProperRatingBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="22sp"
        android:textStyle="bold"
        app:prb_defaultRating="4"
        app:prb_symbolicTick="$"
        app:prb_symbolicTickNormalColor="@android:color/darker_gray"
        app:prb_symbolicTickSelectedColor="@android:color/holo_green_dark"
        app:prb_totalTicks="5"
        />
```

becomes this:

![Readme Screenshot 1](https://raw.githubusercontent.com/techery/ProperRatingBar/master/readme_imgs/readme1.png)

## Example
See video below for possible usage scenarios.

![Demo Gif](https://raw.githubusercontent.com/techery/ProperRatingBar/master/readme_imgs/ProperRatingBar_v001_demo.gif)


## Motivation
Why bother and design our own element?

Well, stock Android RatingBar lacks some styling attributes that you would expect from it and sometimes offers unexpected behavior.

Besides that, we needed to use symbols like '$' as rating bar ticks in our project - none of third-patry libraries out there provides that functionality.

## Installation

We use jitpack, so you can use github sources in your module's `build.gradle` file:

`compile 'com.github.techery:ProperRatingBar:xxxyyyzzzw'` where xxxyyyzzzw = last 10 digits of desired commit to build.
Also you need to add jitpack as repository in the same file:
```groovy
repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
```

## API Reference
So far we only support customising via xml layout.
Here is the list of applicable attributes:
+ ``prb_totalTicks``: total number of ticks to show. Default is '5'
+ ``prb_defaultRating``: use this to set rating from xml. Default is '3'
+ ``prb_clickable``: if set to 'true' - use will be able to change rating by clicking. Default is 'false'

+ ``prb_symbolicTick``: symbol to be used as a tick. Default is '$'
+ ``android:textSize``: text size of symbolic tick. Default is '15sp'
+ ``android:textStyle``: text style of symbolic tick. Possible: 'bold', 'italic', 'normal'. Default is 'normal'
+ ``prb_symbolicTickNormalColor``: color of symbolic tick that is not selected (not rated). Default is '#FF000000' (black)
+ ``prb_symbolicTickSelectedColor``: color of symbolic tick that is selected (rated). Default is '#FF888888' (gray)

+ ``prb_tickNormalDrawable``: drawable resource to use as a tick that is not selected (not rated). No default value
+ ``prb_tickSelectedDrawable``: drawable resource to use as a tick that is selected (rated). No default value
+ ``prb_tickSpacing``: margin to be applied to tick drawables. Only applies to drawable-type ticks. Default is '1dp' (gray)

Also there's a number of methods to operate on ProperRatingBar programmatically:
```java
/**
* Get the attached {@link RatingListener}
* @return listener or null if none was set
*/
@Nullable
public RatingListener getListener();

/**
* Set the {@link RatingListener} to be called when user taps rating bar's ticks
* @param listener listener to set
*/
public void setListener(RatingListener listener);

/**
* Remove listener
*/
public void removeRatingListener();

/**
* Get the current rating shown
* @return rating
*/
public int getRating();

/**
* Set the rating to show
* @param rating new rating value
*/
public void setRating(int rating);
```

## Tests

No test coverage is supplied so far - might be added later.

## Versions

Current version is set to 0.0.1
Consider this to be itinial commit meaning that given code is likely to be refactored and partly re-done (including API changing with no backward-compatibility).

After that versionName will gain some more convincing and reliable number like "1.0" and API will be frozen.

## License

    The MIT License (MIT)

    Copyright (c) 2015 Techery (http://techery.io/)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
