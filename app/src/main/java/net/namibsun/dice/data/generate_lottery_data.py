#!/usr/bin/env python
"""
Copyright 2015-2017 Hermann Krumrey

This file is part of android-dice.

android-dice is an Android app that allows a user to roll a virtual
die. Multiple configurations are supported

android-dice is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

android-dice is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with android-dice. If not, see <http://www.gnu.org/licenses/>.
"""

import requests
from bs4 import BeautifulSoup


# The KDOC Comment for the generated values
KDOC_COMMENT = "/**\n" \
               " * Stores the most commonly drawn lottery numbers\n" \
               " * from the German lottery '6 AUS 49.\n" \
               " */\n"


def main():
    """
    Fetches the Lottery statistics from http://www.dielottozahlende.net
    and writes them to the file 'LotteryData.kt
    """

    with open("../../../../../../../"
              "resources/LICENSE_HEADER_KOTLIN", 'r') as f:
        license_header = f.read()

    with open("LotteryData.kt", 'w') as kotlin:

        kotlin.write(license_header + "\n")
        kotlin.write("package net.namibsun.dice.data\n\n")
        kotlin.write(KDOC_COMMENT)
        kotlin.write("val lotteryOccurences: HashMap<Int, Int> = hashMapOf(\n")

        req = requests.get("http://www.dielottozahlende.net/"
                           "lotto/6aus49/statistiken/haeufigkeit"
                           "%20der%20lottozahlen.html").text
        soup = BeautifulSoup(req, 'html.parser')

        stats = soup.findAll("table")[1].findAll("tr")
        for (i, number) in enumerate(stats):

            try:

                info = number.findAll("td")
                value = info[0].text
                ocurences = info[1].text

                tab = "        "

                if i == len(stats) - 1:
                    kotlin.write(tab + value + " to " + ocurences + "\n)")
                else:
                    kotlin.write(tab + value + " to " + ocurences + ",\n")

            except TypeError:
                pass


if __name__ == "__main__":
    main()
    print("Done")
