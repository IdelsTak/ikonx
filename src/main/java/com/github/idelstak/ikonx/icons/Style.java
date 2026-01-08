/*
 * The MIT License
 * Copyright © 2026 Hiram K. <https://github.com/IdelsTak>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.idelstak.ikonx.icons;

public sealed interface Style {

    String displayName();

    record Filled() implements Style {

        @Override
        public String displayName() {
            return "Filled";
        }
    }

    record Regular() implements Style {

        @Override
        public String displayName() {
            return "Regular";
        }
    }

    record Solid() implements Style {

        @Override
        public String displayName() {
            return "Solid";
        }
    }

    record Bold() implements Style {

        @Override
        public String displayName() {
            return "Bold";
        }
    }

    record ExtraBold() implements Style {

        @Override
        public String displayName() {
            return "Extra Bold";
        }
    }

    record Outlined() implements Style {

        @Override
        public String displayName() {
            return "Outlined";
        }
    }

    record Round() implements Style {

        @Override
        public String displayName() {
            return "Round";
        }
    }

    record Sharp() implements Style {

        @Override
        public String displayName() {
            return "Sharp";
        }
    }

    record Stroke() implements Style {

        @Override
        public String displayName() {
            return "Stroke";
        }
    }

    record Square() implements Style {

        @Override
        public String displayName() {
            return "Square";
        }
    }

    record Logo() implements Style {

        @Override
        public String displayName() {
            return "Logo";
        }
    }

    record Brand() implements Style {

        @Override
        public String displayName() {
            return "Brand";
        }
    }

    record Monochrome() implements Style {

        @Override
        public String displayName() {
            return "Monochrome";
        }
    }

    record Line() implements Style {

        @Override
        public String displayName() {
            return "Line";
        }
    }

    record Alternate() implements Style {

        @Override
        public String displayName() {
            return "Alt";
        }
    }

    record All() implements Style {

        @Override
        public String displayName() {
            return "All";
        }
    }
}
