import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/tcr">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Tcr
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tcr-stake">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Tcr Stake
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/wallet-token">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Wallet Token
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/identity">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Identity
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/identity-profile">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Identity Profile
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
