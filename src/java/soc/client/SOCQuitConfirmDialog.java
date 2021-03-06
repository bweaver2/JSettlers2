/**
 * Java Settlers - An online multiplayer version of the game Settlers of Catan
 * Copyright (C) 2003  Robert S. Thomas
 * This file copyright (C) 2007,2008,2010,2013 Jeremy D Monin <jeremy@nand.net>
 * Portions of this file Copyright (C) 2012-2013 Paul Bilnoski <paul@bilnoski.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package soc.client;

import soc.client.SOCPlayerClient.GameAwtDisplay;
import soc.game.SOCGame;
import soc.util.SOCStringManager;


/**
 * This is the modal dialog to confirm when someone clicks the Quit Game button.
 *
 * @author Jeremy D Monin <jeremy@nand.net>
 */
@SuppressWarnings("serial")
class SOCQuitConfirmDialog extends AskDialog
{
    /** i18n text strings.
     *  @since 2.0.00
     */
    private static final SOCStringManager strings = SOCStringManager.getClientManager();

    /**
     * Creates and shows a new SOCQuitConfirmDialog.
     * If the game is over, the "Quit" button is the default;
     * otherwise, Continue is default.
     *
     * @param cli      Player client interface
     * @param gamePI   Current game's player interface
     * @throws IllegalArgumentException If cli or gamePI is null
     */
    public static void createAndShow(GameAwtDisplay cli, SOCPlayerInterface gamePI)
        throws IllegalArgumentException
    {
        if ((cli == null) || (gamePI == null))
            throw new IllegalArgumentException("no nulls");
        SOCGame ga = gamePI.getGame();
        boolean gaOver = (ga.getGameState() >= SOCGame.OVER);

        SOCQuitConfirmDialog qcd = new SOCQuitConfirmDialog(cli, gamePI, gaOver);
        qcd.setVisible(true);
    }
    

    /**
     * Creates a new SOCQuitConfirmDialog.
     *
     * @param cli      Player client interface
     * @param gamePI   Current game's player interface
     * @param gameIsOver The game is over - "Quit" button should be default (if not over, Continue is default)
     */
    protected SOCQuitConfirmDialog(GameAwtDisplay cli, SOCPlayerInterface gamePI, boolean gameIsOver)
    {
        super(cli, gamePI,
            strings.get("dialog.quit.really", gamePI.getGame().getName()),  // "Really quit game {0}?"
            strings.get(gameIsOver
                ? "dialog.quit.finished"        // "Do you want to quit this finished game?"
                : "dialog.quit.being.played"),  // "Do you want to quit the game being played?"
            strings.get("dialog.quit.this"),    // "Quit this game"
            strings.get(gameIsOver
                ? "dialog.quit.dont"            // "Don't quit"
                : "dialog.base.continue.playing"),  // "Continue playing"
            ((gamePI.getGame().getGameState() != SOCGame.NEW)
                ? strings.get("dialog.quit.reset.board")  // "Reset board"
                : null),
            (gameIsOver ? 1 : 2));
    }

    /**
     * React to the Quit button. (call playerInterface.leaveGame)
     */
    @Override
    public void button1Chosen()
    {
        pi.leaveGame();
    }

    /**
     * React to the Continue button. (Nothing to do)
     */
    @Override
    public void button2Chosen()
    {
        // Nothing to do (continue playing)
    }

    /**
     * React to the Reset Board button. (call playerInterface.resetBoardRequest)
     */
    @Override
    public void button3Chosen()
    {
        pi.resetBoardRequest(false);
    }

    /**
     * React to the dialog window closed by user. (Nothing to do)
     */
    @Override
    public void windowCloseChosen()
    {
        // Nothing to do (continue playing)
    }

}
