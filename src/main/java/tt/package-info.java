/**
 * javadoc conventions:
 *
 * ba : bat of player 1 {@link tt.pojo.RallyAction#BAT_A}<br>
 * bb : bat of player 2 {@link tt.pojo.RallyAction#BAT_B}<br>
 * sa : ball hits table on player 1 {@link tt.pojo.RallyAction#SIDE_A}<br>
 * sb : ball hits table of player 2 {@link tt.pojo.RallyAction#SIDE_B}<br>
 * n : hits the net  {@link tt.pojo.RallyAction#NET}<br>
 * [x] : repeated x times
 *
 * example rally (ending with a ball in the net stay on the side the ball was hit)
 * <pre>
 * ba sa sb bb sa
 * (ba sb bb sa)[5]
 * ba sb bb n sb sb
 * </pre>
 */
package tt;

