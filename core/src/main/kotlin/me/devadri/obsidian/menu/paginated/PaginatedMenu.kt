package me.devadri.obsidian.menu.paginated

import me.devadri.obsidian.ExperimentalAPI
import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.inventory.InventorySize
import me.devadri.obsidian.menu.Menu
import me.devadri.obsidian.menu.button.MenuButton
import me.devadri.obsidian.menu.coords.Coordinates
import net.kyori.adventure.text.Component

@ExperimentalAPI
abstract class PaginatedMenu<T> protected constructor(
    val list: List<T>,
    title: Component,
    plugin: ObsidianPlugin,
    size: InventorySize = InventorySize.THREE_ROWS
) : Menu(title, plugin, size) {

    abstract val slots: Array<Int>

    abstract val nextPageButton: MenuButton
    abstract val previousPageButton: MenuButton
    abstract val currentPageButton: MenuButton

    private var _currentPage: Int = 0
    var currentPage: Int
        get() = _currentPage
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException("Current page must be positive.")
            }

            if (value > maxPage) {
                throw IllegalArgumentException("Current page must be lower than max page ($maxPage).")
            }

            _currentPage = value
        }

    val maxPage: Int
        get() = list.size / slots.size

    abstract val buttonForT: (T) -> MenuButton

    internal fun calculateIndexes(page: Int): Pair<Int, Int> {
        val fromIndex = if (page == 0) 0 else page * slots.size
        val toIndex = if (page == 0)
            if (slots.size > list.size) list.size else slots.size
        else (page + 1) * slots.size

        return Pair(fromIndex, toIndex)
    }

    /**
     * If you override this method, make sure to call super.setup()
     */
    override fun setup() {
        addButton(nextPageButton, previousPageButton, currentPageButton)

        fillPage()

        super.setup()
    }

    open fun fillPage() {
        clearPage()

        val (fromIndex, toIndex) = calculateIndexes(currentPage)

        val sublist = list.subList(
            if (fromIndex > list.size) (list.size - slots.size) else fromIndex,
            if (toIndex > list.size) list.size else toIndex
        )

        for ((i, t) in sublist.withIndex()) {
            val button1 = buttonForT(t)
            val realButton = MenuButton(
                button1.plugin,
                button1.item,
                Coordinates.fromSlot(slots[i]),
                button1.onClick
            )

            buttons.add(realButton)
        }
    }

    open fun clearPage() {
        for (slot in slots) {
            inventory.setItem(slot, null)
        }
    }
}