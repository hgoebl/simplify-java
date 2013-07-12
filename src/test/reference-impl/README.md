# Testdata Generator for simplify-java #

This package creates testdata (as reference) for the Java library 'hgoebl/simplify-java'.

 * Input is read from lib/test-data.js
 * Output is generated to ../resources

# Update of test-data #

    # fetch current simplify.js (npm version is out-dated and doesn't support node.js)
    wget --output-document=lib/simplify.js \
        --no-check-certificate \
        https://raw.github.com/mourner/simplify-js/master/simplify.js

    wget --output-document=lib/test-data.js http://mourner.github.io/simplify-js/website/test-data.js

    echo "module.exports = points;" >> lib/test-data.js

# TODO #

update dependencies when simplify-js is published on npm

    "dependencies": {
      "simplify-js": "~1.1.0"
    }

