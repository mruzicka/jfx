/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.javafx.font;

import com.sun.javafx.PlatformUtil;
import com.sun.javafx.geom.transform.BaseTransform;

public class PrismFontUtils {

    private PrismFontUtils() {
    }

    private static float lcdContrast = -1;

    public static float getLCDContrast() {
        if (lcdContrast == -1) {
            if (PlatformUtil.isWindows()) {
                lcdContrast = PrismFontFactory.getLCDContrast() / 1000f;
            } else {
                /* REMIND: When using CoreText it likely already applies gamma
                 * correction to the glyph images. The current implementation does
                 * not take this into account when rasterizing the glyph. Thus,
                 * it is possible gamma correction is been applied twice to the
                 * final result.
                 * Consider using "1" for lcdContrast possibly produces visually
                 * more appealing results (although not strictly correct).
                 */
                lcdContrast = 1.3f;
            }
        }
        return lcdContrast;
    }

    static Metrics getFontMetrics(PGFont font) {
        FontStrike strike = font.getStrike(BaseTransform.IDENTITY_TRANSFORM,
                                           FontResource.AA_GREYSCALE);
        return strike.getMetrics();
    }

    static double computeStringWidth(PGFont font, String string) {
        if (string == null || string.equals("")) return 0;
        FontStrike strike = font.getStrike(BaseTransform.IDENTITY_TRANSFORM,
                                           FontResource.AA_GREYSCALE);
        double width = 0f;
        for (int i = 0; i < string.length(); i++) {
            width += strike.getCharAdvance(string.charAt(i));
        }
        return width;
    }
}