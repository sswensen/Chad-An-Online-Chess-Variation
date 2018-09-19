// Note the name of this file has X.test.js. Jest looks for any files with this pattern.

/*  First we import our Enzyme configuration (1), this is defined in a different
 *    file and is require to use Enzyme for components. In addition to the standard
 *    imports you've seen before, we are using Enzyme.shallow (2), this "renders"
 *    your component but only for the first layer in the DOM (ie. <Itinerary/> will
 *    just render <Itinerary/> even though it may have more components under it.).
 *    Shallow rendering prevents problems with lower components from showing up in
 *    the tests of parent components.
*/

import './enzyme.config.js'                   // (1)
import React from 'react'
import { mount } from 'enzyme'              // (2)
import Options from '../src/components/Application/Options'

/* Both of these tests are functionally identical although the standard way
 *  of writing tests uses lambda or anonymous functions. These are useful
 *  for defining functions that will only be in your code once but may be
 *  called multiple times by whatever they are passed to.
*/

/* A test response for our client to use;
 * this object represents the props that would be passed to the Options
 * component on construction.
 */
const startProps = {
  'config': { 'units': ['miles', 'kilometers'] },
  'options': { 'unit': 'miles' }
};

/* Test example using a pre-defined function */
function testExample() {
  const options = mount((
      <Options config={startProps.config} options={startProps.options}/>
    ));

  let actual = [];
  options.find('Button').map((element) => actual.push(element.prop('value')));

  expect(actual).toEqual(startProps.config.units);
}

test('Check to see if table gets made correctly (Function)', testExample);

/*--------------------------------------------------------------------------*/

/* Test example using an anonymous function */
test('Check to see if table gets made correctly (Lambda)', () => {
  /*  First, we create a version of our Options component, using the
   *  startProps object defined above for its props (1). With our new unrendered
   *  component, we can call ReactWrapper.find() to extract a certain part
   *  of the component and its children (2). Lastly, we check to see if the
   *  value of the buttons created by the component is what we expect,
   *  given the example input (3).
  */
  const options = mount((   // (1)
      <Options config={startProps.config} options={startProps.options}/>
    ));

  let actual = [];
  options.find('Button').map((element) => actual.push(element.prop('value')));  // (2)

  expect(actual).toEqual(startProps.config.units);  // (3)
});
