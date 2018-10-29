import React, {Component} from 'react';
import {Collapse, Navbar, NavbarBrand, Nav, NavItem, NavLink as ReactNavLink, Button} from 'reactstrap';
import { NavLink } from 'react-router-dom'
import './css/navbar.css';

class Navigation extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
    };

    this.toggle = this.toggle.bind(this);
    this.collapsable = this.collapsable.bind(this);
    this.getToggler = this.getToggler.bind(this);

    this.renderNavItem = this.renderNavItem.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  getToggler(){
    if (!this.state.isOpen)return (<div>&#x2630;</div>);
    else return (<div>&#x268A;</div>);
  }

  /**
   * Renders the mobile navigation bar.  This will also update the webpage title
   *      to the correct heading.  The "homepage" has a different name than the
   *      other items.  Hence the additional logic.
   * @param href weblink to bind to the name.
   * @param clickable link true if the link redirects to another site.
   * @param index of the mapping
   * @param menu menu name
   * @param title of the nav bar item.
   * @returns {XML} of the nav bar item.
   */
  renderNavItem(href, clickable, index, menu, title) {
    const toLink = (index === 0) ? '/' : menu.replace(/\s+/g, '-').toLowerCase();
    // true if link redirects user.
    let navLink = (clickable)
        ? (<ReactNavLink className="dropdown_item" href={href} target="_blank">{title}</ReactNavLink>)
        : (<NavLink to={toLink} exact className="dropdown_item nav-link">
               <div onClick={(e) => this.toggle()}>{title}</div>
           </NavLink>);
    return ( <NavItem key={title}>{ navLink }</NavItem> );
  }

  collapsable(){
    let toggler = this.getToggler();
    const navItems = [
      this.renderNavItem("", false, 0, "TripCo", "TripCo" )
    ]
    return(
      <div>
        <Navbar className="nav_side_bar" light>
          <Button className="dropdown_icon" onClick={this.toggle}> {toggler}</Button>
          <Collapse isOpen={this.state.isOpen} navbar>
            <Nav className="ml-auto" navbar>
              {navItems}
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }

  render() {
    let sidebar = this.collapsable();

    const navItems = [];

    return(
      <div className="application-width">
        {sidebar}
        <Navbar className="nav_bar">
            <NavbarBrand className="nav_title" href="/">
              T00 TripCo
            </NavbarBrand>
            <div>
              {navItems.reverse()}
            </div>
        </Navbar>
      </div>
    )
  }

}

export default Navigation;