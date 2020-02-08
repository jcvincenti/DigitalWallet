package view

import exceptions.CantDeleteCurrentUserException
import exceptions.UserNotSelectedException
import org.uqbar.arena.windows.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import viewModel.*

/**
 *  @Author Fabian Frangella, Federico Garetti
 *
 *  User administration Window
 *  Features:
 *  Visualize user
 *  Add user
 *  Modify user
 *  Delete user
 */
class UserAdministrationWindow(owner: WindowOwner, model: UserAdministrationViewModel)
    : Dialog<UserAdministrationViewModel>(owner, model) {

    /**
     * Function to create all the panels that the mainPanel contain
     */
    override fun createFormPanel(mainPanel: Panel) {
        title = "Digital Wallet - Administration"
        createUserFilterPanel(mainPanel)
        createUserListPanel(mainPanel)
        Panel(mainPanel) with {
            asHorizontal()
            createButtonsPanel(it)
        }
    }

    /**
     * Function to create the filter panel
     *
     */
    private fun createUserFilterPanel(owner: Panel) {
        Panel(owner) with {
            width = 200
            Label(it) with {
                fontSize = 14
                text = "Users List"
                alignLeft()
            }
            Panel(owner) with {
                asHorizontal()
                Label(it) with {
                    text = "Filter by Name: "
                    alignLeft()
                }
                TextBox(it) with {
                    width = 150
                    bindTo("userFilter")
                    alignLeft()
                }
            }
        }
    }

    /**
     * Function to create the panel containing the list of all users with the name written on the filter textbox
     * Note: if nothing is written on the filter it show all users
     */
    private fun createUserListPanel(owner: Panel) {
        Panel(owner) with {
            setMinWidth(400)
            table<UserViewModel>(it) with {
                bindItemsTo("filteredUsers")
                bindSelectionTo("selectedUser")
                column {
                    fixedSize = 75
                    title = "Name"
                    bindContentsTo("userName")
                }
                column {
                    fixedSize = 75
                    title = "Last Name"
                    bindContentsTo("userLastName")
                }
                column {
                    fixedSize = 120
                    title = "User Email"
                    bindContentsTo("userEmail")
                }

                column {
                    fixedSize = 90
                    title = "State"
                    bindContentsTo("status")
                }
            }
        }
    }

    /**
     * Function to create the buttons panel
     * Note: the buttons "Visualize", "Add" and "Modify" open a new window
     * the button "Delete" eliminates the user selected on the table
     */
    private fun createButtonsPanel(owner: Panel) {
        Panel(owner) with {
            asHorizontal()
            Button(owner) with {
                caption = "Visualize"
                onClick {
                    try {
                        thisWindow.modelObject.validateUserSelected()
                        VisualizeUserWindow(thisWindow, thisWindow.modelObject.selectedUser!!).open()
                    } catch (e : UserNotSelectedException) {
                        thisWindow.showInfo(e.message)
                    }
                }
            }

            Button(owner) with {
                caption = "Add"
                var userAdministrationVM = thisWindow.modelObject
                onClick {
                    AddUserWindow(thisWindow, AddUserViewModel()) with {
                        onAccept {
                            userAdministrationVM.register(thisWindow.modelObject.getUser())
                        }
                        open()
                    }
                }
            }
            Button(owner) with {
                caption = "Modify"
                var userAdministrationVM = thisWindow.modelObject
                onClick {
                    try {
                        userAdministrationVM.validateUserSelected()
                        ModifyUserWindow(thisWindow, userAdministrationVM.selectedUser!!) with {
                            onAccept {
                                userAdministrationVM.modify(thisWindow.modelObject)
                                thisWindow.showInfo("The user "
                                        + userAdministrationVM.selectedUser!!.getUserName() + " " +
                                        userAdministrationVM.selectedUser!!.getUserLastName() +  " has been modified")
                            }
                            open()
                        }
                    } catch (e : UserNotSelectedException) {
                        thisWindow.showInfo(e.message)
                    }
                }
            }
        }
        Button(owner) with {
            caption = "Delete"
            var userAdministrationVM = thisWindow.modelObject
            onClick {
                try {
                    userAdministrationVM.validateUserSelected()
                    DeleteUserWindow(thisWindow, thisWindow.modelObject.selectedUser!!) with {
                        onAccept {
                            try {
                                userAdministrationVM.deleteUser(userAdministrationVM.selectedUser!!)
                                thisWindow.showInfo("The user "
                                        + userAdministrationVM.selectedUser!!.getUserName() + " " +
                                        userAdministrationVM.selectedUser!!.getUserLastName() + " has been deleted")
                            } catch (e: CantDeleteCurrentUserException){
                                thisWindow.showInfo(e.message)
                            }
                        }
                        open()
                    }
                } catch (e : UserNotSelectedException) {
                    thisWindow.showInfo(e.message)
                }
            }
        }
        Button(owner) with {
            caption = "Back to Menu"
            var dw = thisWindow.modelObject.digitalWallet
            onClick {
                thisWindow.close()
                MainMenuWindow(thisWindow,
                        MainMenuViewModel(LoginViewModel(dw,thisWindow.modelObject.currentUser))).open()
            }
        }
    }
}

